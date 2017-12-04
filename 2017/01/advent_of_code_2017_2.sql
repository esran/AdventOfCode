create or replace function advent_of_code_2017_2 (input text) returns bigint as $$
  select sum(n::integer)
  from (select n from (select n, row_number() over () as rowid from (select unnest(regexp_split_to_array(input, '')) n) q) t
        where exists (select 1 from (select n, row_number() over () as rowid from (select unnest(regexp_split_to_array(input, '')) n) q) t1
          where t1.n::integer = t.n::integer and t1.rowid = case when t.rowid + length(input) / 2 <= length(input) then t.rowid + length(input) / 2 else t.rowid + length(input) / 2 - length(input) end
      )) qt;
$$ language SQL;
