package ru.bestrestaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bestrestaurant.AuthorizedUser;
import ru.bestrestaurant.model.Restaurant;
import ru.bestrestaurant.model.User;
import ru.bestrestaurant.model.Vote;
import ru.bestrestaurant.repository.DataJpaRestaurantRepository;
import ru.bestrestaurant.repository.DataJpaUserRepository;
import ru.bestrestaurant.repository.DataJpaVoteRepository;
import ru.bestrestaurant.to.VoteTo;
import ru.bestrestaurant.util.DateTimeUtil;
import ru.bestrestaurant.util.UtilTo;
import ru.bestrestaurant.util.exception.NotAllowedOpException;
import ru.bestrestaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    static final String REST_URL = "/api/profile/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DataJpaRestaurantRepository restaurantRepo;

    private final DataJpaVoteRepository voteRepo;

    private final DataJpaUserRepository userRepo;

    public ProfileRestController(DataJpaRestaurantRepository restaurantRepo,
                                 DataJpaVoteRepository voteRepo, DataJpaUserRepository userRepo) {
        this.restaurantRepo = restaurantRepo;
        this.voteRepo = voteRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("votes")
    public List<VoteTo> getHistoryOfVotes(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromD,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toD,
            @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Get history of votes from {} to {} for user {}", fromD, toD, authUser.getId());
        LocalDate[] correctInterval = DateTimeUtil.interval(fromD, toD);
        List<Vote> votes = voteRepo.getAll(authUser.getId(), correctInterval[0], correctInterval[1]);
        return UtilTo.getVoteTos(votes);
    }

    @PostMapping("restaurants/{id}")
    @Transactional
    public ResponseEntity<VoteTo> voteForRestaurant(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Voted for restaurant {} by user {}", id, authUser.getId());
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found"));
        User user = userRepo.findById(authUser.getId())
                .orElseThrow(() -> new NotFoundException("User with id=" + authUser.getId() + " not found"));
        Vote vote = voteRepo.getByUserIdAndDate(user.getId(), LocalDate.now());
        if (vote == null) {
            Vote newVote = new Vote(user, restaurant);
            return new ResponseEntity<>(UtilTo.createVoteTo(
                    voteRepo.save(newVote)), HttpStatus.CREATED);
        } else {
            if (DateTimeUtil.isThresholdExceeded()) {
                throw new NotAllowedOpException("Votes are not allowed after 11:00 AM");
            }
            Vote updated = new Vote(user, restaurant);
            updated.setId(vote.id());
            return new ResponseEntity<>(UtilTo.createVoteTo(
                    voteRepo.save(updated)), HttpStatus.OK);
        }
    }
}
