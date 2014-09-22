[when]there is an userRule with=$userRule:UserRule($rc:ruleCondition)

[when]there is an order with=$order:Order()
[when]- expr_101=currency==$rc.getString("currency")
[when]- expr_102=amount>$rc.getDouble("minOrderAmount")
[when]- expr_103=amount>=$rc.getDouble("minOrderAmount")
[when]- expr_104=amount==$rc.getDouble("amount")
[when]- expr_105=amount<=$rc.getDouble("maxOrderAmount")
[when]- expr_106=amount<$rc.getDouble("maxOrderAmount")
[when]- expr_107=source==$rc.getString("source")
[when]- expr_108=itemIdExistInCondition($order.items,$rc.getList("itemIds"))
[when]- expr_109=$rc.getList("destIncludes") contains destination
#[when]order item id is in selective=exists (Item($rc.itemIds contains id) from $order.items)

[then]there is out test=System.out.println("finished rule for " + $userRule.getUserCode());
[then]there is an order=System.out.println("there is an order of "+$order.getName());
[then]accept applicant {name}=System.out.println("accept "+$order.getName());

[then]reject applicant {name} because your age less than {minAge}=System.out.println("reject "+$a.getName()+", because your age < 18");

[when]applied after {deadline}=$a:Application(dateApplied>"{deadline}")
[then]reject application of {name}=System.out.println("reject application exceeded deadline "+$a.getDateApplied());