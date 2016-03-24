--drop table public.db_payment

CREATE TABLE public.db_payment
(
   payment_id serial primary key, 
   payment_creator_konto integer NOT NULL, 
   payment_borrower_konto integer NOT NULL, 
   payment_text text NOT NULL, 
   payment_betrag numeric(10,2) NOT NULL, 
   payment_datum text NOT NULL
   payment_status integer NOT NULL DEFAULT 0,
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.db_payment
  OWNER TO dev;