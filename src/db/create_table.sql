-- Table: public.clientassessment

-- DROP TABLE IF EXISTS public.clientassessment;

CREATE TABLE IF NOT EXISTS public.clientassessment
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    inn character varying COLLATE pg_catalog."default",
    details character varying COLLATE pg_catalog."default",
    success boolean,
    CONSTRAINT clientassessment_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clientassessment
    OWNER to postgres;