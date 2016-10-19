package elchanchoverde.tripboard.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import elchanchoverde.tripboard.Fragments.ItemsFragments;
import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 19/10/2016.
 */
public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            ItemsFragments itemsFragments = new ItemsFragments();
            itemsFragments.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_item, itemsFragments)
                    .commit();
        }
    }
}
