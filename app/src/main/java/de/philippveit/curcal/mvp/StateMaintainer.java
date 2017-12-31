package de.philippveit.curcal.mvp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by pveit on 27.12.2017.
 */

public class StateMaintainer {
    protected final String TAG = getClass().getSimpleName();

    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragementManager;
    private StateMngFragment mStateMaintainerFrag;


    /**
     * Constructor
     * @param fragementManager
     * @param stateMaintenerTag
     */
    public StateMaintainer(FragmentManager fragementManager, String stateMaintenerTag) {
        this.mStateMaintainerTag = stateMaintenerTag;
        this.mFragementManager = new WeakReference<FragmentManager>(fragementManager);
    }


    public boolean firstTimeIn() {
        try {
            // Recovering the reference
            mStateMaintainerFrag = (StateMngFragment) mFragementManager.get().findFragmentByTag(mStateMaintainerTag);

            if(mStateMaintainerFrag == null){
                Log.d(TAG, "Creating a new ReatinedFragment " + mStateMaintainerTag);

                mStateMaintainerFrag = new StateMngFragment();
                mFragementManager.get().beginTransaction().add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                return true;
            }else {
                Log.d(TAG, "Returns a existent reatined fragment existente "  + mStateMaintainerTag);
                return false;
            }
        }catch (NullPointerException e){
            Log.w(TAG, "Error firstTimeIn()");
            return false;
        }
    }

    /**
     * Insert Object to be preserved during configuration change
     * @param key   Object's TAG reference
     * @param obj   Object to maintain
     */
    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    /**
     * Insert Object to be preserved during configuration change
     * Uses the Object's class name as a TAG reference
     * Should only be used one time by type class
     * @param obj   Object to maintain
     */
    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }


    /**
     * Recovers saved object
     * @param key   TAG reference
     * @param <T>   Class type
     * @return      Objects
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    /**
     * Verify the object existence
     * @param key   Obj TAG
     */
    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }


    /**
     * Save and manages objects that show be preserved during configuration changes
     *
     */
    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Grants that the frag will be preserved
            setRetainInstance(true);
        }

        /**
         * Insert Objects
         *
         * @param key refernece TAG
         * @param obj Object to be saved
         */
        public void put(String key, Object obj){
            mData.put(key, obj);
        }

        /**
         * Insert objects using class name as TAG
         *
         * @param obj object to save
         */
        public void put(Object obj){
            put(obj.getClass().getName(), obj);
        }

        /**
         * Recover obj
         * @param key
         * @param <T>
         * @return
         */
        public <T> T get(String key){
            return (T) mData.get(key);
        }
    }
}
