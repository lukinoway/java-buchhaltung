CREATE TABLE public.db_transaktion_rechnung
(
   rechnung_id serial primary key, 
   transaktion_id integer, 
   rechnung_data bytea, 
   rechnung_data_extension text
) 
WITH (
  OIDS = FALSE
)
;