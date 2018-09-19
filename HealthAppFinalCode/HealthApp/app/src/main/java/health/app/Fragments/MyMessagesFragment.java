package health.app.Fragments;



import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import health.app.R;

public class MyMessagesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MyMessagesFragment() {

    }
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView tab1;TextView tab2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_messages, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_login_signup);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.bell).setVisible(false).setEnabled(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_message, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_message:

                break;
        }
        return true;

    }

    private void setupTabIcons() {
        final RelativeLayout tabOne = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tab1 = (TextView) tabOne.findViewById(R.id.tab);
        tab1.setText("INBOX");
        tab1.setCompoundDrawablePadding(20);
        tab1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        tab1.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.inboxwhite2x, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        final RelativeLayout tabTwo = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab2, null);
        tab2 = (TextView) tabTwo.findViewById(R.id.tab2);
        tab2.setText("SENT");
        tab2.setCompoundDrawablePadding(20);
        tab2.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.senticon2x, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0)
                {
                    View view = tab.getCustomView();
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.custom_tab);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_selected));
                    tab1 = (TextView) view.findViewById(R.id.tab);
                    tab1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    tab1.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.inboxwhite2x, 0, 0, 0);
                }
                else {
                    View view = tab.getCustomView();
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.custom_tab2);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_selected));
                    tab2 = (TextView) view.findViewById(R.id.tab2);
                    tab2.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    tab2.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.sentwhite2x, 0, 0, 0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0) {
                    View view = tab.getCustomView();
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.custom_tab);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
                    tab1 = (TextView) view.findViewById(R.id.tab);
                    tab1.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_light_gray));
                    tab1.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.inboxgrey2x, 0, 0, 0);
                }
                else {
                    View view = tab.getCustomView();
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.custom_tab2);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
                    tab2 = (TextView) view.findViewById(R.id.tab2);
                    tab2.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_light_gray));
                    tab2.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.senticon2x, 0, 0, 0);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new InboxFragment(), "INBOX");
        adapter.addFrag(new SentFragment(), "SENT");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return mFragmentTitleList.get(position);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
