package elchanchoverde.tripboard.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import elchanchoverde.tripboard.Fragments.CountryFragment;
import elchanchoverde.tripboard.Fragments.ItemsFragments;
import elchanchoverde.tripboard.R;

public class MainActivity extends AppCompatActivity implements CountryFragment.callBacks {

    private static final String ELEMENTS_TAG = "ELEMENTS";
    public boolean mTwoPane;

    //Indica cuando existen dos fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.list_item) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_item, new ItemsFragments(),
                                ELEMENTS_TAG)
                        .commit();
            } else {
                mTwoPane = false;
            }
        }

    }

    public boolean getmTwoPane() {
        return this.mTwoPane;
    }


    @Override
    public void onItemSelected(String nombreCountry, String country) {
        if (mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putString("nombreCountry", nombreCountry);
            bundle.putString("country", country);
            ItemsFragments itemsFragments = new ItemsFragments();
            itemsFragments.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.list_item, itemsFragments).
                    commit();
        } else {
            Intent intent = new Intent(this, ItemList.class);
            intent.putExtra("nombreCountry", nombreCountry);
            intent.putExtra("country", country);
            startActivity(intent);
        }
    }
}
