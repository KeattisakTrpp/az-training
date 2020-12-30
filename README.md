## Post Method
### User
`/users/signup` <br>
**return** User Object
> example request body
```
{
	"user": {
		"firstname": "",
		"lastname": "",
		"email": "",
		"citizenId": "",
		"password": ""
	},
	"pet": {
		"name": "",
		"age": 
	}
}
```

`/users/login` <br>
**return** User Object with token { user: { }, token: { } }
> example request body: 
```
{
  "email": "",
  "password": ""
}
```

`/users/{userId}/buy` <br>
**return** User Object
> example request body: 
```
{
  "productId": "",
  "petId": ""
}
```

`/users/{userId}/claim` <br>
**return** Pet Object
> example request body: 
```
{
	"amount": 80000,
	"budgetId": "",
	"petId": "",
	"claimType": "ACCIDENT" // (ACCIDENT, OPD)
}
```

`/users/{userId}/pet` add pet <br>
**return** Pet Object
> example request body: 
```
{
	"name": 80000,
	"age": 5,
	"type": "", // optional (DOG, CAT) default is DOG
}
```
### Product
`/products` add product <br>
> example request body: 
```
{
	"name": "product1",
	"opd": 50000,
	"accident": 100000,
	"price": 1200,
	"duration": 2
}
```
## Get Method
### User
`/users` get all users <br>
**return** [Object]
> response data : 
```
[
  {
    "user": {
      "firstname": "",
      "lastname": "",
      "email": "",
      "citizenId": "",
      "password": ""
    },
    "pet": {
      "name": "",
      "age": 
    }
  }
]
```

`/users/{userId}/pet` get pets from userId <br>
**return** [Object]
> response data : 
```
 [
  {
    "id": "5fe9dea997190f4f20f296ac",
    "name": "ong",
    "age": 1,
    "productList": [],
    "type": "DOG"
  }
]
```
## Product
`/products` get all products <br>
**return** [Object]
> response data : 
```
[
  {
    "id": "5fec877e9c852b13643ec442",
    "name": "product1",
    "opd": 50000,
    "accident": 100000,
    "price": 1200,
    "duration": 2
  }
]
```
## History

`/history/{userId}/purchase` get history pruchase from userId <br>
**return** [Object]
> response data : 
```
[
  {
    "id": "5fec8ba009a455254f6d58a4",
    "userId": "5fec53eb1aa3ca6ba5f5d299",
    "productDetails": {
      "id": "5fec877e9c852b13643ec442",
      "name": "product1",
      "opd": 50000,
      "accident": 100000,
      "price": 1200,
      "duration": 2
    },
    "purchaseDate": "2020-12-30T14:16:00.646+00:00"
  }
]
```

`/history/{userId}/claim` get history claim from userId <br>
**return** [Object]
> response data : 
```
[
  {
    "id": "5fec9c8ce6984e189a0128c6",
    "date": "2020-12-30T15:28:12.351+00:00",
    "product": {
      "id": "5fec8ba009a455254f6d58a5",
      "name": "product1",
      "opd": 50000,
      "accident": 100000,
      "price": 1200,
      "buyDate": "2020-12-30T14:16:00.688+00:00",
      "expDate": "2022-12-30T14:16:00.688+00:00"
    },
    "pet": {
      "id": "5fec53ea1aa3ca6ba5f5d298",
      "name": "Bo",
      "age": 1,
      "productList": [
        {
          "id": "5fec8ba009a455254f6d58a6",
          "product": {
            "id": "5fec8ba009a455254f6d58a5",
            "name": "product1",
            "opd": 50000,
            "accident": 100000,
            "price": 1200,
            "buyDate": "2020-12-30T14:16:00.688+00:00",
            "expDate": "2022-12-30T14:16:00.688+00:00"
          },
          "usedBudget": {
            "opd": 0,
            "accident": 100000
          }
        }
      ],
      "type": "DOG"
    },
    "userId": "5fec53eb1aa3ca6ba5f5d299",
    "amount": 20000,
    "type": "ACCIDENT",
    "status": "PENDING"
  }
]
```
