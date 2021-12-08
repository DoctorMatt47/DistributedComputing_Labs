CREATE TABLE Producer (
    id integer PRIMARY KEY,
    name character varying(255) NOT NULL
);

CREATE TABLE Product (
     id integer PRIMARY KEY,
     producerId integer REFERENCES Producer (id) ON DELETE CASCADE,
     name character varying(255) NOT NULL,
     price integer NOT NULL,
     type character varying(255) NOT NULL
);
