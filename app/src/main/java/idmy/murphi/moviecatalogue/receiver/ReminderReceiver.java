package idmy.murphi.moviecatalogue.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import idmy.murphi.moviecatalogue.R;
import idmy.murphi.moviecatalogue.data.local.model.Movie;
import idmy.murphi.moviecatalogue.data.local.model.MoviesResponse;
import idmy.murphi.moviecatalogue.data.remote.api.MovieApiClient;
import idmy.murphi.moviecatalogue.data.remote.api.MovieService;
import idmy.murphi.moviecatalogue.ui.activities.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY = "DailyReminder";
    public static final String TYPE_RELEASED = "ReleasedReminder";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_MESSAGE = "message";

    private final int ID_DAILY = 100;
    private final int ID_RELEASED = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);

        if (type != null) {
            if (type.equals(TYPE_DAILY)) {
                String message = intent.getStringExtra(EXTRA_MESSAGE);
                showDailyNotification(context, TYPE_DAILY, message, ID_DAILY);
            } else if (type.equals(TYPE_RELEASED)) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault());
                String formattedDate = df.format(c);
                MovieService movieService = MovieApiClient.getClient();

                Call<MoviesResponse> request;
                request = movieService.releasedToday(formattedDate, formattedDate);
                request.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        List<Movie> movieList;
                        MoviesResponse data = response.body();
                        movieList = data != null ? data.getMovies() : Collections.emptyList();

                        if (movieList != null) {
                            for (Movie movie : movieList) {
                                showDailyNotification(context, TYPE_RELEASED, movie.getTitle() + context.getString(R.string.released_reminder), Math.toIntExact(movie.getId()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

                    }
                });
            }
        }
    }

    public void setDailyReminder(Context context, String type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, context.getString(R.string.daily_reminder));
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf("07"));
        calendar.set(Calendar.MINUTE, Integer.valueOf("00"));
        calendar.set(Calendar.SECOND, Integer.valueOf("00"));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setReleasedReminder(Context context, String type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf("07"));
        calendar.set(Calendar.MINUTE, Integer.valueOf("59"));
        calendar.set(Calendar.SECOND, Integer.valueOf("30"));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASED, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASED;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showDailyNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_access_time_black)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }
}
