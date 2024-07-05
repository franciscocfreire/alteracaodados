### Request
``` curl
curl --location 'http://localhost:8080/alteration/process' \
--header 'Content-Type: application/json' \
--data '{
"cpf": "33998291830",
"token": null,
"metadata": {}
}'
```

### Response

```
{
    "metadata": {
        "phone": "maskedphone",
        "email": "maskedemail@example.com"
    },
    "nextState": "FACIAL_BIOMETRIC",
    "state": "INITIAL",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGF0ZS10b2tlbiIsInN0YXRlIjoiSU5JVElBTCIsIm5leHRTdGF0ZSI6IkZBQ0lBTF9CSU9NRVRSSUMiLCJ1c2VybmFtZSI6IjMzOTk4MjkxODMwIiwiaWF0IjoxNzIwMTg1MDAxLCJleHAiOjE3MjAxODUzMDF9.DV51eU9cD_fHJLAPuuuKguXbBcop6qb07t8vYxEbEC0"
}
```
