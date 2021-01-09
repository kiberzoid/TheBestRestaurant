##Restaurant's voting application
#### API documentation
#####Guest/User/Admin:
Get all restaurants: 
- path ``/api/restaurants``
- method ``GET``
- example request
```
curl "http://localhost:8080/TheBestRestaurant/api/restaurants" -H "Accept: application/json"
```
- example response
```
[
	{
		"restaurant_id":1,
		"restaurant_name":"Dodici",
		"restaurant_address":"Rozhdestvenskaya 1"
	},
	{
		"restaurant_id":2,
		"restaurant_name":"Vitalich",
		"restaurant_address":"Pokrovskay 12"
	},
	{
		"restaurant_id":3,
		"restaurant_name":"Praha",
		"restaurant_address":"Pokrovskay 145"
	}
]
```
Get all menus: 
- path ``/api/restaurants/menus``
- method ``GET``
- example request menus for today
```
curl "http://localhost:8080/TheBestRestaurant/api/restaurants/menus" -H "Accept: application/json" 
```
- example response
```
{
	"2021-01-06":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Ginger Chicken",
					"meal_price":22.99
				},
				{
					"meal_name":"Green Salad",
					"meal_price":4.99
				}
			]
		},
		{
			"restaurant_id":2,
			"restaurant_name":"Vitalich",
			"restaurant_menu":[
				{
					"meal_name":"Quesadilla With Chicken",
					"meal_price":5.99
				},
				{
					"meal_name":"Filet Of Beef",
					"meal_price":49.99
				}
			]
		}
	]
}

```
- example request menus for period 2021-01-05 - 2021-01-07
```
curl "http://localhost:8080/TheBestRestaurant/api/restaurants/menus?startDate=2021-01-05&endDate=2021-01-07" -H "Accept: application/json" 
```
- example response
```
{
    "2021-01-06":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Ginger Chicken",
					"meal_price":22.99
				},
				{
					"meal_name":"Green Salad",
					"meal_price":4.99
				}
			]
		},
		{
			"restaurant_id":2,
			"restaurant_name":"Vitalich",
			"restaurant_menu":[
				{
					"meal_name":"Quesadilla With Chicken",
					"meal_price":5.99
				},
				{
					"meal_name":"Filet Of Beef",
					"meal_price":49.99
				}
			]
		}
	],
	"2021-01-05":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Flank Steak",
					"meal_price":22.99
				},
				{
					"meal_name":"Russian Borsch",
					"meal_price":7.99
				}
			]
		},
		{
			"restaurant_id":2,
			"restaurant_name":"Vitalich",
			"restaurant_menu":[
				{
					"meal_name":"Caesar salad",
					"meal_price":9.99
				},
				{
					"meal_name":"Parmesan Chicken",
					"meal_price":16.49
				}
			]
		},
		{
			"restaurant_id":3,
			"restaurant_name":"Praha",
			"restaurant_menu":[
				{
					"meal_name":"Crab Salad",
					"meal_price":23.0
				},
				{
					"meal_name":"Minestrone",
					"meal_price":11.99
				}
			]
		}
	]
}
```
Get single restaurant's menu
- path ``/api/restaurants/1/menus``
- method ``GET``
- example request menu for today
```
curl "http://localhost:8080/TheBestRestaurant/api/restaurants/1/menus" -H "Accept: application/json" 
```
- example response
```
{
	"2021-01-06":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Ginger Chicken",
					"meal_price":22.99
				},
				{
					"meal_name":"Green Salad",
					"meal_price":4.99
				}
			]
		}
	]
}
```
- example request menus for period 2021-01-05 - 2021-01-07
```
curl "http://localhost:8080/TheBestRestaurant/api/restaurants/1/menus?startDate=2021-01-05&endDate=2021-01-07" -H "Accept: application/json" 
```
- example response
```
{
	"2021-01-06":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Ginger Chicken",
					"meal_price":22.99
				},
				{
					"meal_name":"Green Salad",
					"meal_price":4.99
				}
			]
		}
	],
	"2021-01-05":[
		{
			"restaurant_id":1,
			"restaurant_name":"Dodici",
			"restaurant_menu":[
				{
					"meal_name":"Flank Steak",
					"meal_price":22.99
				},
				{
					"meal_name":"Russian Borsch",
					"meal_price":7.99
				}
			]
		}
	]
}
```
#####User:
Get user's history votes: 
- path ``/api/profile/votes``
- method ``GET``
- example request user's vote for 
```
curl "http://localhost:8080/TheBestRestaurant/api/profile/votes" -H "Accept: application/json" --user user1@mail.ru:user1
```
- example response
```
[
	{
		"vote_id": 15,
		"date": "2021-01-06",
		"restaurant_id": 1,
		"restaurant_name": "Dodici"
	}
]
```
- example request user's votes for period 2021-01-04 - 2021-01-07
```
curl "http://localhost:8080/TheBestRestaurant/api/profile/votes?startDate=2021-01-04&endDate=2021-01-07" -H "Accept: application/json" --user user1@mail.ru:user1
```
- example response
```
[
	{
		"vote_id": 5,
		"date": "2021-01-05",
		"restaurant_id": 1,
		"restaurant_name": "Dodici"
	},
	{
		"vote_id": 3,
		"date": "2021-01-04",
		"restaurant_id": 3,
		"restaurant_name": "Praha"
	}
]
```
Vote for restaurant
- path ``/api/profile/restaurants/1``
- method ``POST``
- example request 
```
curl -X POST "http://localhost:8080/TheBestRestaurant/api/profile/restaurants/1" -H "Accept: application/json" --user user1@mail.ru:user1
```
- example response
```
{
	"vote_id": 7,
	"date": "2021-01-06",
	"restaurant_id": 1,
	"restaurant_name": "Dodici"
}
```
#####Admin:
Create a restaurant: 
- path ``/api/admin/restaurants``
- method ``POST``
- example request 
```
curl -X POST "http://localhost:8080/TheBestRestaurant/api/admin/restaurants" -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"name\": \"New Restaurant\",\"address\": \"New Address\"}" --user admin1@mail.ru:admin1
```
- example response
```
{
	"restaurant_id": 6,
	"restaurant_name": "New Restaurant",
	"restaurant_address": "New Address"
}
```
Update the restaurant: 
- path ``/api/admin/restaurants/4``
- method ``PUT``
- example request 
```
curl -X PUT "http://localhost:8080/TheBestRestaurant/api/admin/restaurants/4" -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"name\": \"Updated Restaurant\",\"address\": \"Updated Address\"}" --user admin1@mail.ru:admin1
```
Delete the restaurant: 
- path ``/api/admin/restaurants/4``
- method ``DELETE``
- example request 
```
curl -X DELETE "http://localhost:8080/TheBestRestaurant/api/admin/restaurants/4" -H "Accept: application/json" --user admin1@mail.ru:admin1
```
Create a menu for the restaurant: 
- path ``/api/admin/restaurants/4/menu``
- method ``POST``
- example request 
```
curl -X POST "http://localhost:8080/TheBestRestaurant/api/admin/restaurants/4/menu" -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"restaurant_menu\": [{\"meal_name\": \"Soup\",\"meal_price\": 13.49},{\"meal_name\": \"Coffee\",\"meal_price\": 2.00}]}" --user admin1@mail.ru:admin1
```
- example response
```
{
	"2021-01-06": [
		{
			"restaurant_id": 4,
			"restaurant_name": "Olivier",
			"restaurant_menu": [
				{
					"meal_name": "Soup",
					"meal_price": 13.49
				},
				{
					"meal_name": "Coffee",
					"meal_price": 2
				}
			],
		}
	],
}
```
Update a menu of the restaurant: 
- path ``/api/admin/restaurants/4/menu``
- method ``PUT``
- example request 
```
curl -X PUT "http://localhost:8080/TheBestRestaurant/api/admin/restaurants/4/menu" -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"restaurant_menu\": [{\"meal_name\": \"Cream Soup\",\"meal_price\": 13.49},{\"meal_name\": \"Tea\",\"meal_price\": 2.00}]}" --user admin1@mail.ru:admin1
```
Delete a menu of the restaurant: 
- path ``/api/admin/restaurants/4``
- method ``DELETE``
- example request 
```
curl -X DELETE "http://localhost:8080/TheBestRestaurant/api/admin/restaurants/4/menu" -H "Accept: application/json" --user admin1@mail.ru:admin1
```