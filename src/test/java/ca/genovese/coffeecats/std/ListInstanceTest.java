package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

/**
 * Test that the ListInstance follows the CovariantFunctorLaws.
 */
@ExtendWith(ListInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class ListInstanceTest implements CovariantFunctorLaws<List> {

}

