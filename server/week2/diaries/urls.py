from django.urls import path
from . import views

app_name = "diaries"

urlpatterns = [
    path("feeds/", views.feeds, name="feeds"),
    path("diary/", views.diary, name="diary"),
    path("similar_feeds/", views.similar_feeds, name="similar-feeds"),
    path("opposite_feeds/", views.opposite_feeds, name="opposite-feeds"),
    path("day_diary/", views.day_diary, name="day-diary")
]