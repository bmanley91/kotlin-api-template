CREATE TABLE IF NOT EXISTS users (
    id              UUID            DEFAULT gen_random_uuid(),
    username        VARCHAR(100)    NOT NULL,
    hashed_password VARCHAR(255)    NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);