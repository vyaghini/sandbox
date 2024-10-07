CREATE TABLE LOCATION (
    GRADLE_ID NUMERIC PRIMARY KEY,         
    BUILDING_NAME VARCHAR NOT NULL,       
    FULL_ADDRESS VARCHAR NOT NULL,        
    COORDINATES VARCHAR                   
);

CREATE TABLE USER_PREFENCE (
    USER_ID NUMERIC PRIMARY KEY,         
    GRADLE_ID NUMERIC PRIMARY KEY,                 
);

-- opentable/otj-pg-embedded or zonkyio/embedded-postgres
