[when]there is an orderAutoFlowRule with=$orderRule:OrderAutoFlowRule()

[when]there is an order with=$order:Order()
[when]- currency is selective=currency==$orderRule.currency
[when]- amount greater than selective=amount>$orderRule.orderAmount
[when]- amount greater than or equals selective=amount>=$orderRule.orderAmount
[when]- amount equals selective=amount==$orderRule.orderAmount
[when]- amount lesser than or equals selective=amount<=$orderRule.orderAmount
[when]- amount lesser than selective=amount<$orderRule.orderAmount
[when]- order come from selective platform=source==$orderRule.orderSource
[when]- order item id is in selective=itemIdExistInCondition($order.items,$orderRule.itemIds)
#[when]order item id is in selective=exists (Item($orderRule.itemIds contains id) from $order.items)

[then]there is out test=System.out.println("come to THEN ");
[then]there is an order=System.out.println("there is an order of "+$order.getName());
[then]accept applicant {name}=System.out.println("accept "+$order.getName());

[then]reject applicant {name} because your age less than {minAge}=System.out.println("reject "+$a.getName()+", because your age < 18");

[when]applied after {deadline}=$a:Application(dateApplied>"{deadline}")
[then]reject application of {name}=System.out.println("reject application exceeded deadline "+$a.getDateApplied());