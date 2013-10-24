alter table product add column (sku varchar(255));

-- add priority field in category
alter table category add column (priority int);
update category set priority = 1;