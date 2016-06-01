package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.laws.InvariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(ListInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class ListInstanceTest implements InvariantFunctorLaws<List> {

}

