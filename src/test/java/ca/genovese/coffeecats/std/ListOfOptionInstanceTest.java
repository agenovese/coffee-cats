package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.laws.InvariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(ListOfOptionInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class ListOfOptionInstanceTest implements InvariantFunctorLaws<Kind<List, Option>> {

}

