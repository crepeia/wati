
UPDATE wati.tb_user SET in_ranking=FALSE
WHERE in_ranking IS NULL

UPDATE wati.tb_user SET nickname=""
WHERE nickname IS NULL
