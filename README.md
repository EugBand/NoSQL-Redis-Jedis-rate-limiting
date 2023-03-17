# API Rate Limiting With Bucket4J and Redis
 
- [Bucket4J](https://github.com/bucket4j/bucket4j) library to implement it and use as a distributed cache 
- [Redis](https://redis.io/)  - here we stored Key Values are stored as well as cached Mysql User Table also.
- Mysql - here we stored users limit. For example User A is allowed to call 10 API calls in 1 Minute, User B is allowed to call 50 API Calls in 1 Minute

### The Token Bucket Algorithm
Token Bucket is an algorithm that can use to implement rate limiting. In short, it works as follows:

1. A bucket is created with a certain capacity (number of tokens).
2. When a request comes in, the bucket is checked. If there is enough capacity, the request is allowed to proceed. Otherwise, the request is denied.
3. When a request is allowed, the capacity is reduced.
4. After a certain amount of time, the capacity is replenished.

### Prerequisite

- Use docker-compose.yaml from folder `docker` to set up necessary env.   
- Database, table and two test users will be created by Flyway when Spring boot app starts.
- Use Postman collection from folder `postman` for testing
