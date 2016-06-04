package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(ListInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class ListInstanceTest implements CovariantFunctorLaws<List> {

}

