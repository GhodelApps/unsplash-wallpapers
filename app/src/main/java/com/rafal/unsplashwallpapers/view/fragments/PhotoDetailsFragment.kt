package com.rafal.unsplashwallpapers.view.fragments

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.rafal.unsplashwallpapers.R
import com.rafal.unsplashwallpapers.databinding.FragmentPhotoDetailsBinding
import com.rafal.unsplashwallpapers.util.Resource
import com.rafal.unsplashwallpapers.view.viewmodels.PhotoDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: PhotoDetailsFragmentArgs by navArgs()

    private val viewModel: PhotoDetailsViewModel by viewModels()
    private lateinit var photoUrl: String

    private lateinit var photoBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPhoto(args.photoID)

        viewModel.photoLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { photo ->
                        binding.apply {
                            photoDetailsPb.visibility = View.GONE
                            photoDetailsSuccessLayout.visibility = View.VISIBLE
                            photoDetailsUserTv.text = photo.user.name
                            photoDetailsAperture.text =
                                photo.exif.aperture ?: getString(R.string.unknown)
                            photoDetailsCamera.text = photo.exif.make ?: getString(R.string.unknown)
                            photoDetailsCameraModel.text =
                                photo.exif.model ?: getString(R.string.unknown)
                            photoDetailsFocalLength.text =
                                photo.exif.focal_length ?: getString(R.string.unknown)
                            photoDetailsIso.text = photo.exif.iso.toString()
                            photoDetailsDimensions.text = "${photo.width} x ${photo.height}"
                            photoDetailsLikes.text = photo.likes.toString()
                            photoDetailsDownloads.text = photo.downloads.toString()
                        }
                        photoUrl = photo.urls.regular
                        loadImage(photoUrl)
                        loadUserImage(photo.user.profile_image.large)
                    }
                }
                is Resource.Fail -> {
                    binding.apply {
                        photoDetailsPb.visibility = View.GONE
                        photoDetailsFailLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.photoDetailsRetryBtn.setOnClickListener {
            binding.apply {
                photoDetailsPb.visibility = View.VISIBLE
                photoDetailsFailLayout.visibility = View.GONE
            }

            viewModel.getPhoto(args.photoID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                photoUrl?.let { url ->
                    shareImageUrl(url)
                }
                true
            }
            R.id.action_wallpaper -> {
                val setWallpaperDialog = AlertDialog.Builder(requireContext())
                    .apply {
                        setMessage(R.string.set_as_wallpaper_message)
                        create()
                        setPositiveButton(R.string.yes) { dialog, id ->
                            setImageAsWallpaper()
                            dialog.cancel()
                        }
                        setNegativeButton(R.string.no) { dialog, id ->
                            dialog.cancel()
                        }
                    }
                setWallpaperDialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .error(R.drawable.ic_baseline_error_24)
            .apply(
                RequestOptions()
                    .override(Target.SIZE_ORIGINAL)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .transform(RoundedCorners(50))
            )
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoDetailsProgress.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoDetailsProgress.visibility = View.GONE
                    return false
                }
            })
            .transition(BitmapTransitionOptions.withCrossFade())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    photoBitmap = resource
                    binding.photoDetailsIv.setImageBitmap(photoBitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun loadUserImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .error(R.drawable.ic_baseline_error_24)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.photoDetailsUserIv)
    }

    private fun shareImageUrl(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun setImageAsWallpaper() {
        val wallpaperManager = WallpaperManager.getInstance(activity)
        try {
            wallpaperManager.setBitmap(photoBitmap)
            Toast.makeText(
                requireContext(),
                R.string.wallpaper_successfully_set,
                Toast.LENGTH_SHORT
            ).show()
        } catch (exception: Exception) {
            Toast.makeText(
                requireContext(),
                R.string.failed_to_set_wallpaper,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}