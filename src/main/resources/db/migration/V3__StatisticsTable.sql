-- SEQUENCE: public.shortened_path_redirection_id_seq

-- DROP SEQUENCE public.shortened_path_redirection_id_seq;

CREATE SEQUENCE public.shortened_path_redirection_id_seq;

ALTER SEQUENCE public.shortened_path_redirection_id_seq
    OWNER TO sample;

-- Table: public.shortened_path_redirection

-- DROP TABLE public.shortened_path_redirection;

CREATE TABLE public.shortened_path_redirection
(
    id bigint NOT NULL DEFAULT nextval('shortened_path_redirection_id_seq'::regclass),
    date timestamp without time zone,
    redirected_path character varying(255) COLLATE pg_catalog."default",
    request_path character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT shortened_path_redirection_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.shortened_path_redirection
    OWNER to sample;
    

-- SEQUENCE: public.shortened_path_update_id_seq

-- DROP SEQUENCE public.shortened_path_update_id_seq;

CREATE SEQUENCE public.shortened_path_update_id_seq;

ALTER SEQUENCE public.shortened_path_update_id_seq
    OWNER TO sample;
    
-- Table: public.shortened_path_updat

-- DROP TABLE public.shortened_path_update;

CREATE TABLE public.shortened_path_update
(
    id bigint NOT NULL DEFAULT nextval('shortened_path_update_id_seq'::regclass),
    date timestamp without time zone,
    redirected_path character varying(255) COLLATE pg_catalog."default",
    request_path character varying(255) COLLATE pg_catalog."default",
    update_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT shortened_path_update_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.shortened_path_update
    OWNER to sample;