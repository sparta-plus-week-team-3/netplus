USE netplus;
USER DATABASE

DROP TABLE netplus.queries;

create table netplus.queries
(
    query_id bigint auto_increment
        primary key,
    content  varchar(255) null
);

DELIMITER $$
DROP PROCEDURE IF EXISTS loopInsertQuery$$
CREATE PROCEDURE loopInsertQuery()
BEGIN
    DECLARE i INT DEFAULT 1;
START TRANSACTION;
WHILE i <= 50000 DO
            INSERT INTO queries(content)
            VALUES (
                       concat('SearchQuery', i)
                   );
            SET i = i + 1;
END WHILE;
COMMIT;
END$$
DELIMITER ;

CALL loopInsertQuery();