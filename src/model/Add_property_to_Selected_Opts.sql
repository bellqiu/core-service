alter table SelectedOpts add column (create_date TIMESTAMP default CURRENT_TIMESTAMP);
alter table SelectedOpts add column (update_date TIMESTAMP);
alter table SelectedOpts add column (status VARCHAR(45));

 ALTER TABLE hb_order DROP FOREIGN KEY fk_Order_Coupon1;  
 
 ALTER TABLE hb_order DROP column Coupon_id;
 
  ALTER TABLE hb_order MODIFY  User_id bigint null;
  
  ALTER TABLE orderitem MODIFY  Order_id bigint null;
  
    ALTER TABLE SelectedOpts MODIFY  OrderItem_id bigint null;
  
  