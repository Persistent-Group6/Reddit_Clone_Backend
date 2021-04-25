USE redditdb;
INSERT INTO user (username, password, email, created, enabled)
VALUES (
        "rahul",
        "rahul",
        "rahulsahoo66@gmail.com",
        "2019-09-14 22:58:11",
        TRUE
    );
INSERT INTO user (username, password, email, created, enabled)
VALUES (
        "rohan",
        "rohan",
        "rohansahoo66@gmail.com",
        "2020-10-14 22:58:11",
        FALSE
    );
SELECT *
FROM user;