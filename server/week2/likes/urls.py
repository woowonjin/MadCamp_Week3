from django.urls import path
from . import views

app_name = "likes"

urlpatterns = [
    path("", views.like, name="likes")
]