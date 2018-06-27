package com.platzi.platzivideos;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.platzi.platzivideos.ui.MainActivity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Test
    public void testDataShown() throws InterruptedException {
        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
        RecyclerView recyclerView = activity.findViewById(R.id.video_list);
        assertThat(recyclerView.getAdapter(), notNullValue());
        waitForAdapterChange(recyclerView);
        assertThat(recyclerView.getAdapter().getItemCount(), not(0));
    }

    private void waitForAdapterChange(RecyclerView recyclerView) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            recyclerView.getAdapter().registerAdapterDataObserver(
                    new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                            latch.countDown();
                        }

                        @Override
                        public void onItemRangeInserted(int positionStart, int itemCount) {
                            super.onItemRangeInserted(positionStart, itemCount);
                            latch.countDown();
                        }
                    });

        });

        if (recyclerView.getAdapter().getItemCount() > 0) {
            return;
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), is(true));
    }

}
