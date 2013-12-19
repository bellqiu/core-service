alter table poption add column (customize TINYINT);
alter table poption add column (htmlkey VARCHAR(200));
update poption set customize=0;