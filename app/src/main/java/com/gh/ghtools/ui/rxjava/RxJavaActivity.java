package com.gh.ghtools.ui.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gh.ghtools.R;
import com.gh.ghtools.base.BaseActivity;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: gh
 * @description:
 * @date: 2019-09-19.
 * @from:
 */
public class RxJavaActivity extends BaseActivity {

    private List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 7, 8, 0);
    private List<Integer> list2 = Arrays.asList(5, 6, 3);
    private List<Integer> list3 = Arrays.asList(11, 12, 13, 9);
    private List<Student> studentList;

    public static void access(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, RxJavaActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        studentList = new ArrayList<>();
        studentList.add(new Student("0", "张一", "22", "1"));
        studentList.add(new Student("1", "张二", "12", "2"));
        studentList.add(new Student("2", "张三", "25", "1"));
        studentList.add(new Student("3", "张四", "32", "3"));
        studentList.add(new Student("4", "张五", "52", "2"));
        studentList.add(new Student("5", "张六", "5", "1"));
        studentList.add(new Student("6", "张七", "20", "3"));
        studentList.add(new Student("7", "张八", "29", "3"));
    }

    @OnClick({R.id.test_01, R.id.test_02, R.id.test_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_01:
                //将list的数据一个一个发送出去
                Observable.just(list1, list2, list3)
                        //这样使用,以后可以减少双层for循环
                        .flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
                            @Override
                            public ObservableSource<Integer> apply(List<Integer> integers) throws Exception {
                                return Observable.fromIterable(integers);
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d("gh____", integer + "");
                            }
                        });
                break;
            case R.id.test_02:
                //将list的数据进行过滤
                Observable.just(list1, list2, list3)
                        //这样使用,以后可以减少双层for循环
                        .flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
                            @Override
                            public ObservableSource<Integer> apply(List<Integer> integers) throws Exception {
                                return Observable.fromIterable(integers);
                            }
                        })
                        .filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Exception {
                                return integer != 3;
                            }
                        })
                        .toList()
                        .subscribe(new Consumer<List<Integer>>() {
                            @Override
                            public void accept(List<Integer> integers) throws Exception {
                                Log.d("gh____", integers.toString());
                            }
                        });

                break;
            case R.id.test_03:
                Flowable
                        .just(studentList)
                        .flatMap(new Function<List<Student>, Publisher<Student>>() {
                            @Override
                            public Publisher<Student> apply(List<Student> students) throws Exception {
                                return Flowable.fromIterable(students);
                            }
                        })
                        .filter(new Predicate<Student>() {
                            @Override
                            public boolean test(Student student) throws Exception {
                                return "2".equals(student.getClazz());
                            }
                        })
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Student>>() {
                            @Override
                            public void accept(List<Student> students) throws Exception {
                                Flowable
                                        .just(students)
                                        .flatMap(new Function<List<Student>, Publisher<Student>>() {
                                            @Override
                                            public Publisher<Student> apply(List<Student> students) throws Exception {
                                                return Flowable.fromIterable(students);
                                            }
                                        })
                                        .subscribe(new Consumer<Student>() {
                                            @Override
                                            public void accept(Student student) throws Exception {
                                                Log.d("gh____", student.getName());
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {

                                            }
                                        });

                            }
                        });

                break;
            default:
                break;
        }
    }

}
