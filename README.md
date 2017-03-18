# RecyclerView控件的用法
在Android开发中我们经常会遇到展示列表的情况，在早期版本系统提供了ListView、GirdView供我们使用，今天来向大家介绍另外一个可以集ListView、GirdView和瀑布流与一身的强大控件RecyclerView。
下面我们先来看一下效果图：
![](https://github.com/Havorld/AndroidSummaryPic/blob/master/RecyclerViewDemo.gif?raw=true)

RecyclerView是新增控件，使用前需要首先在build.gradle中添加support依赖库中，

	dependencies {
	    compile fileTree(include: ['*.jar'], dir: 'libs')
	    testCompile 'junit:junit:4.12'
	    compile 'com.android.support:appcompat-v7:25.1.1'
	    compile 'com.android.support:recyclerview-v7:25.1.1'
	}

### 定义主页面

- 然后我们定义activity_main.xml文件，使用RedioGroup中的三个RadioButton切换三个Fragment来分别展示RecyclerView的ListView、GirdView和瀑布流三种模式。


	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical"
	    tools:context="com.havorld.recyclerviewdemo.MainActivity">
	
	    <RadioGroup
	        android:id="@+id/radioGroup"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:orientation="horizontal"
	        android:padding="10dp">
	
	        <RadioButton
	            android:id="@+id/listView"
	            android:layout_width="0dp"
	            android:layout_height="wrap_content"
	            android:layout_weight="1"
	            android:text="ListView" />
	
	        <RadioButton
	            android:id="@+id/girdView"
	            android:layout_width="0dp"
	            android:layout_height="wrap_content"
	            android:layout_weight="1"
	            android:text="GirdView" />
	
	        <RadioButton
	            android:id="@+id/wallFalls"
	            android:layout_width="0dp"
	            android:layout_height="wrap_content"
	            android:layout_weight="1"
	            android:text="WallFalls" />
	    </RadioGroup>
	
	    <FrameLayout
	        android:id="@+id/frameLayout"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent" />
	
	</LinearLayout>
	

- MainActivity.java代码实现三种模式的切换


	public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
	
	    private FragmentManager fragmentManager;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        RadioGroup radioGroup = $(R.id.radioGroup);
	        radioGroup.setOnCheckedChangeListener(this);
	        fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	        fragmentTransaction.replace(R.id.frameLayout, new ListViewFragment()).commit();
	        radioGroup.check(R.id.listView);
	
	    }
	
	
	    protected <T extends View> T $(int id) {
	
	        //return返回view时,加上泛型T
	        return (T) findViewById(id);
	    }
	
	    @Override
	    public void onCheckedChanged(RadioGroup radioGroup, int i) {
	
	        switch (i) {
	            case R.id.listView:
	                fragmentManager.beginTransaction().replace(R.id.frameLayout, new ListViewFragment()).commit();
	                break;
	            case R.id.girdView:
	                fragmentManager.beginTransaction().replace(R.id.frameLayout, new GirdFragment()).commit();
	                break;
	            case R.id.wallFalls:
	                fragmentManager.beginTransaction().replace(R.id.frameLayout, new WaterFallsFragment()).commit();
	                break;
	            default:
	                break;
	        }
	    }
	}

- 定义抽象类BaseFragment加载定义有RecyclerView控件的布局，要展示的三种模式的Fragment都要继承BaseFragment类并统一实现其中的抽象方法为控件添加Data数据


	public abstract class BaseFragment extends Fragment {
	
	    protected RecyclerView recyclerView;
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	
	        View view = inflater.inflate(R.layout.fragment, null);
	        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
	        initAdapter();
	        return view;
	    }
	
	    public abstract void initAdapter();
	
	    protected List<String> getData() {
	
	        List<String> list = new ArrayList<String>();
	        String[] data = Constant.data;
	        for (int i = 0; i < 50; i++) {
	            int random = new Random().nextInt(data.length);
	            list.add(data[random]);
	        }
	        return list;
	    }
	
	    protected List<String> getWallFallsData() {
	
	        List<String> list = new ArrayList<String>();
	        String[] data = Constant.data;
	        StringBuilder builder;
	        for (int i = 0; i < 50; i++) {
	            builder = new StringBuilder();
	            int random = new Random().nextInt(data.length);
	            for (int j = 0; j < new Random().nextInt(15)+5; j++) {
	                builder.append(data[random] + "\n");
	            }
	            list.add(builder.toString());
	        }
	        return list;
	    }
	}

- 定义有RecyclerView的fragment.xml布局


	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical"
	    tools:context="com.havorld.recyclerviewdemo.MainActivity">
	
	    <android.support.v7.widget.RecyclerView
	        android:id="@+id/recyclerView"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content" />
	
	</LinearLayout>

**RecyclerView可以通过设置setLayoutManager实现三种不同的展示形式，可设置为LinearLayoutManager(ListView形式)，GridLayoutManager(GirdView形式)，StaggeredGridLayoutManager(瀑布流形式)；每一种LayoutManager又可以通过设置setOrientation来设置是横向滑动还是垂直滑动**

### 实现RecyclerView的ListView展示模式

	public class ListViewFragment extends BaseFragment {
	
	    @Override
	    public void initAdapter() {
	
	        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
	        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
	        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	
	        //也可在创建LinearLayoutManager的时候直接设置滑动方向,最后一个参数 reverseLayout表示是否逆序排列展示
	        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
			//设置RecyclerView的展示模式
	        recyclerView.setLayoutManager(linearLayoutManager);
	        MyAdapter myAdapter = new MyAdapter(getActivity(), getData());
	        recyclerView.setAdapter(myAdapter);
	
	        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickLitener() {
	            @Override
	            public void onItemClick(View view, int position) {
	                Toast.makeText(getActivity(), "点击了Item：" + position, Toast.LENGTH_SHORT).show();
	            }
	        });
	
	    }
	
	}




- 新建MyAdapter类并在其中创建MyViewHolder继承ViewHolder，然后再将MyAdapte继承RecyclerView.Adapte并设置泛型为MyAdapter中的MyViewHolder。RecyclerView中没有实现回调，所以最后要自己手动实现回调方法。


	public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
	    private Context context;
	    private List<String> list;
	    private OnItemClickLitener onItemClickLitener;
	
	    public MyAdapter(Context context, List<String> list) {
	        this.context = context;
	        this.list = list;
	    }
	
	    @Override
	    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	        View view = LayoutInflater.from(context).inflate(R.layout.item_list_gird,
	                parent, false);
	        MyViewHolder holder = new MyViewHolder(view);
	        return holder;
	    }
	
	    @Override
	    public void onBindViewHolder(final MyViewHolder holder, int position) {
	        holder.textView.setText(list.get(position));
	
	        holder.itemView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                int position = holder.getAdapterPosition();
	                onItemClickLitener.onItemClick(holder.itemView, position);
	            }
	        });
	        holder.textView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                int position = holder.getAdapterPosition();
	                Toast.makeText(context, "点击了TextView：" + position, Toast.LENGTH_SHORT).show();
	            }
	        });
	
	    }
	
	    @Override
	    public int getItemCount() {
	        return list.size();
	    }
	
	    class MyViewHolder extends ViewHolder {
	
	        private TextView textView;
	
	        public MyViewHolder(View view) {
	            super(view); //这个view就是Item的View
	            textView = (TextView) view.findViewById(R.id.textView);
	        }
	
	    }
	
	
	    public interface OnItemClickLitener {
	
	        void onItemClick(View view, int position);
	    }
	
	    /**
	     * 点击Item回调
	     *
	     * @param onItemClickLitener
	     */
	    public void setOnItemClickListener(OnItemClickLitener onItemClickLitener) {
	
	        this.onItemClickLitener = onItemClickLitener;
	    }
	
	}



- Item的布局item_list_gird.xml


	<?xml version="1.0" encoding="utf-8"?>
	<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:layout_margin="1dp"
	    android:background="@drawable/item_bg">
	
	    <TextView
	        android:id="@+id/textView"
	        android:layout_width="wrap_content"
	        android:layout_height="100dp"
	        android:layout_gravity="center"
	        android:gravity="center"
	        android:text="java" />
	
	</FrameLayout>


### 实现RecyclerView的GirdView展示模式

	public class GirdFragment extends BaseFragment {
	
	    @Override
	    public void initAdapter() {
	
	        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getActivity(),4);
	        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
	        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
	
	        //也可在创建GridLayoutManager的时候直接设置滑动方向,最后一个参数 reverseLayout表示是否逆序排列展示
	       // GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4, GridLayoutManager.VERTICAL, false);
	
	        recyclerView.setLayoutManager(gridLayoutManager);
	        MyAdapter myAdapter = new MyAdapter(getActivity(), getData());
	        recyclerView.setAdapter(myAdapter);
	
	        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickLitener() {
	            @Override
	            public void onItemClick(View view, int position) {
	                Toast.makeText(getActivity(), "点击了Item：" + position, Toast.LENGTH_SHORT).show();
	            }
	        });
	    }
	}

### 实现RecyclerView的瀑布流展示模式

	public class WaterFallsFragment extends BaseFragment {
	
	    @Override
	    public void initAdapter() {
	
	        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
	        //LinearLayoutManager.VERTICAL 垂直滑动，LinearLayoutManager.HORIZONTAL 水平滑动
	        staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
	
	        recyclerView.setLayoutManager(staggeredGridLayoutManager);
	        WallFallsAdapter wallFallsAdapter = new WallFallsAdapter(getActivity(), getWallFallsData());
	        recyclerView.setAdapter(wallFallsAdapter);
	
	        wallFallsAdapter.setOnItemClickListener(new WallFallsAdapter.OnItemClickLitener() {
	            @Override
	            public void onItemClick(View view, int position) {
	                Toast.makeText(getActivity(), "点击了Item：" + position, Toast.LENGTH_SHORT).show();
	            }
	        });
	    }
	}

- 瀑布流Item布局item_wallfalls.xml，注意与前面的不同


	<?xml version="1.0" encoding="utf-8"?>
	<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:layout_margin="1dp"
	    android:background="@drawable/item_bg">
	
	    <TextView
	        android:id="@+id/textView"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:layout_gravity="center"
	        android:gravity="center"
	        android:text="Java" />
	
	</FrameLayout>
OK今天就到这里，这只是RecyclerView很基础的用法，RecyclerView控件功能非常强大后面我们将继续学习RecyclerView的高级用法。
