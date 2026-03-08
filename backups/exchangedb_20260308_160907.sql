--
-- PostgreSQL database dump
--

\restrict eXT6xaoM917x8SJgPl7pFcfXXCxcoghbHdP3n6Ocz7jFi84XZWBZ20MXlJrxC0c

-- Dumped from database version 16.13 (Debian 16.13-1.pgdg13+1)
-- Dumped by pg_dump version 16.13 (Debian 16.13-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: exchange_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exchange_history (
    id bigint NOT NULL,
    from_currency character varying(3) NOT NULL,
    to_currency character varying(3) NOT NULL,
    amount double precision NOT NULL,
    converted_amount double precision NOT NULL,
    rate double precision NOT NULL,
    date date NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.exchange_history OWNER TO postgres;

--
-- Name: exchange_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exchange_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exchange_history_id_seq OWNER TO postgres;

--
-- Name: exchange_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exchange_history_id_seq OWNED BY public.exchange_history.id;


--
-- Name: exchange_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exchange_history ALTER COLUMN id SET DEFAULT nextval('public.exchange_history_id_seq'::regclass);


--
-- Data for Name: exchange_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exchange_history (id, from_currency, to_currency, amount, converted_amount, rate, date, created_at) FROM stdin;
1	USD	PEN	100	374	3.74	2025-04-14	2026-03-07 17:12:13.695473
2	PEN	USD	500	133.69	0.2674	2025-04-14	2026-03-07 17:12:13.695473
3	USD	PEN	250	934.79	3.739	2025-04-15	2026-03-07 17:12:13.695473
4	USD	PEN	100	342.6126	3.426126	2026-03-07	2026-03-07 21:33:09.335943
5	PEN	USD	500	145.9375	0.291875	2026-03-07	2026-03-07 21:33:23.150302
6	USD	PEN	10	34.26126	3.426126	2026-03-07	2026-03-07 21:39:09.942337
7	PEN	USD	100	29.1875	0.291875	2026-03-07	2026-03-07 21:39:24.264641
8	PEN	USD	500	145.9375	0.291875	2026-03-07	2026-03-07 21:55:11.489489
9	USD	PEN	100	342.6126	3.426126	2026-03-07	2026-03-07 21:57:59.572949
10	USD	PEN	99	339.186474	3.426126	2026-03-08	2026-03-08 19:02:11.382716
11	PEN	USD	339.186474	99.000052	0.291875	2026-03-08	2026-03-08 19:03:03.152499
\.


--
-- Name: exchange_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exchange_history_id_seq', 11, true);


--
-- Name: exchange_history exchange_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exchange_history
    ADD CONSTRAINT exchange_history_pkey PRIMARY KEY (id);


--
-- Name: idx_exchange_history_date; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_exchange_history_date ON public.exchange_history USING btree (date);


--
-- Name: idx_exchange_history_from_currency; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_exchange_history_from_currency ON public.exchange_history USING btree (from_currency);


--
-- Name: idx_exchange_history_to_currency; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_exchange_history_to_currency ON public.exchange_history USING btree (to_currency);


--
-- PostgreSQL database dump complete
--

\unrestrict eXT6xaoM917x8SJgPl7pFcfXXCxcoghbHdP3n6Ocz7jFi84XZWBZ20MXlJrxC0c

