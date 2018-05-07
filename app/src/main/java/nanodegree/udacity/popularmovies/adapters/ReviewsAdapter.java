package nanodegree.udacity.popularmovies.adapters;

/**
 * Created by AbdullahAtta on 3/11/2018.
 */
//// The adapter used in showing the reviewer and the reviews of the movies.
//public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.reviewsViewHolder>{
//
//    private List<String> mReviewsContent, mReviewerName;
//    private Context mContext;
//    public ReviewsAdapter(Context context, List<String>reviews ,
//                          List<String>reviewerName){
//        mReviewerName = reviewerName;
//        mReviewsContent = reviews;
//        mContext = context;
//    }
//    @Override
//    public reviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        ReviewsListBinding binding = ReviewsListBinding.inflate(inflater,parent,false);
//
//        return new reviewsViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(reviewsViewHolder holder, int position) {
//        String reviewerName = mReviewerName.get(position);
//        String reviewContent = mReviewsContent.get(position);
//        holder.bindView(reviewerName,reviewContent);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mReviewsContent.size();
//    }
//
//    public class reviewsViewHolder extends RecyclerView.ViewHolder{
//
//        ReviewsListBinding reviewsListBinding;
//        public reviewsViewHolder(ReviewsListBinding binding) {
//            super(binding.getRoot());
//            reviewsListBinding = binding;
//        }
//        public void bindView(String author,String content){
//            reviewsListBinding.reviewerNameTextView.setText(author);
//            reviewsListBinding.reviewContentTextView.setText(content);
//
//            reviewsListBinding.executePendingBindings();
//        }
//    }
//}
