/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
        Intent intent = getIntent();
        String message = intent.getStringExtra(ForecastFragment.EXTRA_TEXT);

        TextView textView = new TextView(this);
        textView.setText(message);
        FrameLayout layout = (FrameLayout) findViewById(R.id.container);
        layout.addView(textView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            return rootView;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

                private static final String LOG_TAG = DetailFragment.class.getSimpleName();

                        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
                private String mForecastStr;

        public DetailFragment() {
                        setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // The detail Activity called via intent.  Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.action_share))
                                        .setText(mForecastStr);
            }

            return rootView;
        }

                        @Override
            public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                  // Inflate the menu; this adds items to the action bar if it is present.
                            inflater.inflate(R.menu.detailfragment, menu);

                            // Retrieve the share menu item
                                    MenuItem menuItem = menu.findItem(R.id.action_share);

                            // Get the provider and hold onto it to set/change the share intent.
                                    ShareActionProvider mShareActionProvider =
                                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

                            // Attach an intent to this ShareActionProvider.  You can update this at any time,
                                    // like when the user selects a new piece of data they might like to share.
                                            if (mShareActionProvider != null ) {
                            mShareActionProvider.setShareIntent(createShareForecastIntent());
                        } else {
                            Log.d(LOG_TAG, "Share Action Provider is null?");
                        }
                   }

                        private Intent createShareForecastIntent() {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                                mForecastStr + FORECAST_SHARE_HASHTAG);
                        return shareIntent;
                    }
    }
}