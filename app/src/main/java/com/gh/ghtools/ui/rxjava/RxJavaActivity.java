package com.gh.ghtools.ui.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gh.ghtools.R;
import com.gh.ghtools.base.BaseActivity;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    @OnClick({R.id.test_01, R.id.test_02, R.id.test_03, R.id.studyrx_01, R.id.studyrx_02, R.id.studyrx_03, R.id.studyrx_04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_01:
                test_01();
                break;
            case R.id.test_02:
                test_02();

                break;
            case R.id.test_03:
                test_03();
                break;
            case R.id.studyrx_01:
                studyRx_01();
                break;
            case R.id.studyrx_02:
                studyRx_02();
                break;
            case R.id.studyrx_03:
                studyRx_03();
                break;
            case R.id.studyrx_04:
                studyRx_04();
                break;
            default:
                break;
        }
    }

    private void test_01() {
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
    }

    private void test_02() {
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
    }

    private void test_03() {
        Observable.just(studentList).toList();
        Observable.fromIterable(studentList).toList();

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
    }

    /**
     * 对rx的学习
     * https://juejin.im/post/582b2c818ac24700618ff8f5
     */
    private void studyRx_01() {
        Observable<Integer> mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observer mObserver = new Observer<Integer>() {
            //这是新加入的方法，在订阅后发送数据之前，
            //回首先调用这个方法，而Disposable可用于取消订阅
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("gh____", "onSubscribe");
            }

            @Override
            public void onNext(Integer o) {
                Log.d("gh____", o.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("gh____", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("gh____", "onComplete");
            }
        };
        mObservable.subscribe(mObserver);
    }

    private void studyRx_02() {
        //Flowable是支持背压的，也就是说，一般而言，上游的被观察者会响应下游观察者的数据请求，下游调用request(n)来告诉上游发送多少个数据。这样避免了大量数据堆积在调用链上，使内存一直处于较低水平。

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Flowable.range(0, 10)
                .subscribe(new Subscriber<Integer>() {
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d("gh____", "onSubscribe start");
                        subscription = s;
                        subscription.request(1);
                        Log.d("gh____", "onSubscribe end");

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("gh____", "onNext" + integer);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("gh____", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("gh____", "onComplete");
                    }
                });
    }

    /**
     * https://juejin.im/post/580103f20e3dd90057fc3e6d
     */
    private void studyRx_03() {
        //创建被观察者
        Observable switcher = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("On");
                emitter.onNext("Off");
                emitter.onNext("On");
                emitter.onNext("On");
                emitter.onComplete();
            }
        });
        Observable switcher2 = Observable.just("On", "Off", "On", "On");
        String[] kk = {"On", "Off", "On", "On"};
        Observable switcher3 = Observable.fromArray(kk);

        //创建观察者
        Observer light = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Subscriber light2 = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        Consumer light3 = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        };

        //订阅
        switcher.subscribe(light);
        switcher.subscribe(light3);

        Observable.just("On", "Off",null, "On", "On")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s != null;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void studyRx_04() {
        int[] intList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        String school[][] = {{"11","12","13"},{"21","22","23"},{"31","32","33"}};
        Observable.fromArray(school)
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String[] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.d("gh____", o);
                    }
                });


    }

}
