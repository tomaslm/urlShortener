-- Table: public.short_url_mapping

-- DROP TABLE public.short_url_mapping;

CREATE TABLE public.short_url_mapping
(
    id bigint NOT NULL,
    created_at timestamp without time zone,
    last_used_at timestamp without time zone,
    real_url character varying(255) COLLATE pg_catalog."default",
    shortened_path character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT short_url_mapping_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.short_url_mapping
    OWNER to sample;