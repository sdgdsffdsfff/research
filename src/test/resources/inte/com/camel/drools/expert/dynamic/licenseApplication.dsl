[keyword]minAge=18
[keyword]specificSex=M
[keyword]deadline=2014-08-01

[when]it has an applicant with sex is {specificSex}=$a:Applicant(sex=="{specificSex}")
[when]if age greater than {minAge} accept applicant=if (age>{minAge}) do[accept]
[when]else reject applicant=else do[reject]
[then]there is an applicant=System.out.println("there is an applicant of "+$a.getName());
[then]accept applicant {name}=System.out.println("accept "+$a.getName());
[then]reject applicant {name} because your age less than {minAge}=System.out.println("reject "+$a.getName()+", because your age < 18");

[when]applied after {deadline}=$a:Application(dateApplied>"{deadline}")
[then]reject application of {name}=System.out.println("reject application exceeded deadline "+$a.getDateApplied());