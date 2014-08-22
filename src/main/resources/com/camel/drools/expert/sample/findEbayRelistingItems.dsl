[when]there is an ERPCondition with=$b:ERPCondition($erp_price:erp_price, $erp_saledNum:erp_saledNum, $erp_status:erp_status)
[when]there is an item with=$a:Item()
[when]- item price greater than price of ERPCondition=price>$erp_price
[when]- item saledNum greater than {erp_saledNum}=saledNum>=$erp_saledNum
[when]- item status equals erp_status=status==$erp_status
[then]print it=System.out.println($a.getTitle()+ " is meet requirement");
[then]this item should be relisting=relistingItems.add($a);
