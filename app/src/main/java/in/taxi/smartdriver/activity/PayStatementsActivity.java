package in.taxi.smartdriver.activity;

import android.os.Bundle;

import in.taxi.smartdriver.R;

public class PayStatementsActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_statements);


        initViews();

        getSupportActionBar().setTitle("Pay Statements");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void initViews() {

    }


}
