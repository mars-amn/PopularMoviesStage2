package nanodegree.udacity.popularmovies.adapters;

/**
 * Created by AbdullahAtta on 3/8/2018.
 */
//
//public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.trailerViewHolder>{
//
//    private static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/";
//    private static final String YOUTUBE_THUMBNAIL_QUALITY_URL = "/sddefault.jpg";
//    private onTrailerClickListener mListener;
//    private onTrailerLongClickListener mLongListener;
//    private Context mContext;
//    private List<String> mTrailersKeys;
//
//        public TrailersAdapter(Context context, List<String> trailers, onTrailerClickListener listener,
//                               onTrailerLongClickListener longListener){
//            mContext = context;
//            mTrailersKeys = trailers;
//            mListener = listener;
//            mLongListener = longListener;
//        }
//
//    public interface onTrailerLongClickListener{
//        void onTrailerLongClick(String key); // to share the trailer link.
//    }
//
//    public interface onTrailerClickListener{
//        void onTrailerClick(String key);
//    }
//
//    @Override
//    public trailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater inflater =LayoutInflater.from(mContext);
//
//        TrailersListBinding binding = TrailersListBinding.inflate(inflater,parent,false);
//        final trailerViewHolder viewHolder= new trailerViewHolder(binding);
//
//        binding.youtubeTrailerThumbnailImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onTrailerClick(mTrailersKeys.get(viewHolder.getAdapterPosition()));
//            }
//        });
//
//        binding.youtubeTrailerThumbnailImageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                mLongListener.onTrailerLongClick(mTrailersKeys.get(viewHolder.getAdapterPosition()));
//                return true;
//            }
//        });
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(trailerViewHolder holder, int position) {
//
//        String trailerThumbnail = YOUTUBE_THUMBNAIL_URL + mTrailersKeys.get(position) +
//                YOUTUBE_THUMBNAIL_QUALITY_URL;
//        holder.bindView(trailerThumbnail);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mTrailersKeys.size();
//    }
//
//    public class trailerViewHolder extends RecyclerView.ViewHolder{
//
//        TrailersListBinding trailersListBinding;
//        public trailerViewHolder(TrailersListBinding binding) {
//            super(binding.getRoot());
//            trailersListBinding = binding;
//        }
//        private void bindView(String trailerThumbnail){
//            GlideApp.with(mContext)
//                    .load(trailerThumbnail)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
//                    .into(trailersListBinding.youtubeTrailerThumbnailImageView);
//
//            trailersListBinding.executePendingBindings();
//        }
//    }
//}
