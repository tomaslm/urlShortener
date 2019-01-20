ALTER TABLE public.short_url_mapping ADD CONSTRAINT uk_shortened_path UNIQUE (shortened_path);
ALTER TABLE public.short_url_mapping ADD CONSTRAINT uk_real_url UNIQUE (real_url);