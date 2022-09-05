-- FUNCTION: public.test$copyby(integer)

-- DROP FUNCTION IF EXISTS public."test$copyby"(integer);



select test$arrayby( string_to_array2('abc,def',',') ) ;
fetch all from rcursor ;

select unnest(string_to_array2('abc,def',','))

CREATE OR REPLACE FUNCTION public.string_to_array2(val character varying, div character varying DEFAULT ','::character varying)
 RETURNS character varying[]
 LANGUAGE plpgsql
 STABLE
AS $function$
begin
	if (val is null or val = '') then 
		return string_to_array(val, div) ; 
	end if ;
	-- return array_remove(string_to_array(val, div,''), '')::varchar[] ;
	return array_remove(array ( select col from (select trim(unnest(string_to_array(val, div,''))) as col) bb where col > ' '    ) , '')::varchar[] ;
end 
$function$

ABORT


select array['abc','def']

select test$firstfn('bleujin', 3, true, string_to_array2('abc,def',','), '{"a":3, "b": "def"}'::jsonb ) ;
FETCH all from rcursor ;

select {'a':1}::jsonb


CREATE OR REPLACE FUNCTION public.test$firstfn(v_name character varying, v_id integer,v_boolean boolean, v_array character varying[], v_json jsonb)
RETURNS refcursor
    LANGUAGE 'plpgsql'
AS $BODY$
	DECLARE 
		rtn_cursor refcursor := 'rcursor';
	BEGIN
		OPEN rtn_cursor FOR
	select v_name AS name, v_id as num, v_boolean as boo, now() as modate, unnest(v_array) as arr, v_json as jso  ;
	
	return rtn_cursor; 

END
$BODY$


CREATE OR REPLACE FUNCTION public.test$firstfn(v_name character varying, v_id integer,v_boolean boolean)
RETURNS refcursor
    LANGUAGE 'plpgsql'
AS $BODY$
	DECLARE 
		rtn_cursor refcursor := 'rcursor';
	BEGIN
		OPEN rtn_cursor FOR
	select v_name AS name, v_id as num, v_boolean as boo, now() as modate ;
	
	return rtn_cursor; 

END
$BODY$



CREATE OR REPLACE FUNCTION public.test$arrayby(v_arr varchar[])
    RETURNS refcursor
    LANGUAGE 'plpgsql'
AS $BODY$
	DECLARE 
		rtn_cursor refcursor := 'rcursor';
	BEGIN
		OPEN rtn_cursor FOR
		Select unnest(v_arr);
		
		return rtn_cursor; 
	END 
$BODY$;
