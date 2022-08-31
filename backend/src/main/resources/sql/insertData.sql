
-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO owner (ID, NAME, CREATED_AT, UPDATED_AT)
VALUES  (1, 'Axel',      CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (2, 'Benni',     CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (3, 'Barbara',   CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (4, 'Dora',      CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (5, 'Rene',      CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (6, 'Kasper',    CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (7, 'Marie',     CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (8, 'Lisa',      CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (9, 'Brunhilde', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
        (10, 'Ferdinand', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());