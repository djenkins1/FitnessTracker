CREATE TABLE public.fitness_week
(
  week_id bigint NOT NULL,
  total_time bigint,
  total_miles double precision,
  total_calories double precision,
  miles_to_date bigint,
  days_exercised character varying(20),
  date_recorded date,
  exercise_type character varying(20),
  created_date timestamp without time zone,
  CONSTRAINT fitness_week_pkey PRIMARY KEY (week_id)
)

CREATE SEQUENCE hibernate_sequence START 2;

-- Sample Data
INSERT INTO fitness_week( week_id , total_time, total_miles , total_calories, miles_to_date, days_exercised , date_recorded, exercise_type, created_date )
VALUES( 1 , 180, 53.0 , 281.2 + 999.9 , 2675 , 'S/M/T/W' , '3/4/2020', 'Cycling' , NOW() ); 

