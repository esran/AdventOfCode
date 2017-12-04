create or replace function advent_of_code_2017_1 (input text) returns bigint as $$
  select sum(n::integer)
  from (select n from (select n, row_number() over () as rowid from (select unnest(regexp_split_to_array(input, '')) n) q) t
        where exists (select 1 from (select n, row_number() over () as rowid from (select unnest(regexp_split_to_array(input, '')) n) q) t1
                      where t1.n::integer = t.n::integer and t1.rowid = greatest(1, (t.rowid + 1) % (select count(*) + 1 from (select n, row_number() over () as rowid from (select unnest(regexp_split_to_array(input, '')) n) q) t2) )
      )) qt;
$$ language SQL;
