alter table HB_Order add column (orderSN varchar(100) unique);
CREATE index trackingId_Index on HB_Order(tacking_id);