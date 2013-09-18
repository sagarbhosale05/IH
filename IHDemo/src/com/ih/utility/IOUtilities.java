package com.ih.utility;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * The Class IOUtilities.
 */
public final class IOUtilities {

	/** The Constant TAG. */
	private static final String TAG = "IOUtilities";

	/** The Constant IO_BUFFER_SIZE. */
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * Gets the external file.
	 * 
	 * @param file
	 *            the file
	 * @return the external file
	 */
	public static File getExternalFile(String file) {
		return new File(Environment.getExternalStorageDirectory(), file);
	}

	public static Boolean checkIfFileExists(String fileName) {
		Boolean isFilePresent = false;
		File file = getExternalFile(fileName);
		if (file != null && file.exists() && file.length() != 0) {
			return isFilePresent = true;
		}
		return isFilePresent;
	}

	/**
	 * Copy the content of the input stream into the output stream, using a
	 * temporary byte array buffer whose size is defined by.
	 * 
	 * @param in
	 *            The input stream to copy from.
	 * @param out
	 *            The output stream to copy to.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 *             {@link #IO_BUFFER_SIZE}.
	 */

	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read()) != -1) {
			out.write(b, 0, read);
		}
	}

	/**
	 * Closes the specified stream.
	 * 
	 * @param stream
	 *            The stream to close.
	 */
	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				Log.e(TAG, "Could not close stream", e);
			}
		}
	}

	/**
	 * Convert InputStream to String.
	 * 
	 * @param is
	 *            the is
	 * @return the string
	 */
	public static String streamToString(java.io.InputStream is) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)); 
		StringBuffer sb = new StringBuffer();
		try {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
		return sb.toString();
	}

	/**
	 * Convert InputStream to Bytes.
	 * 
	 * @param is
	 *            the is
	 * @return the byte[]
	 */

	public static byte[] streamToByteArray(java.io.InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads an image from the cache.
	 * 
	 * @param id
	 *            the id
	 * @param directory
	 *            the directory where the cache is stored
	 * @return The image or null if the file does not exist or if an error
	 *         occurred.
	 */
	public static Bitmap loadImage(String id, File directory) {
		Bitmap image = null;

		final File file = new File(directory, id);
		Log.i("TAG", "file path: " + file.getAbsolutePath());
		if (file.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				image = BitmapFactory.decodeStream(is, null, null);
			} catch (FileNotFoundException e) {
				Log.i("StillsActivity", "msg: " + e.getMessage());
			} finally {
				IOUtilities.closeStream(is);
			}
		}
		Log.i("TAG", "iamge == NUll: " + (image == null));

		return image;
	}

	/**
	 * Load cached image.
	 * 
	 * @param url
	 *            the url
	 * @param directory
	 *            the directory
	 * @return the bitmap
	 */
	public static Bitmap loadCachedImage(String url, File directory) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		if (directory == null) {
			return null;
		}

		Bitmap image = null;
		url = Integer.toString(url.hashCode());

		final File file = new File(directory, url);
		Log.i("TAG", "file path: " + file.getAbsolutePath());
		if (file.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				image = BitmapFactory.decodeStream(is, null, null);
			} catch (FileNotFoundException e) {
				Log.i("StillsActivity", "msg: " + e.getMessage());
			} finally {
				IOUtilities.closeStream(is);
			}
		}
		Log.i("TAG", "iamge == NUll: " + (image == null));

		return image;
	}

	/**
	 * Copy Inputstream to outputstream.
	 * 
	 * @param is
	 *            the is
	 * @param os
	 *            the os
	 */
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}

	}

}
