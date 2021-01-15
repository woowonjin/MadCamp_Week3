from django.contrib import admin
from . import models

# Register your models here.
@admin.register(models.Diary)
class DiaryAdmin(admin.ModelAdmin):
    list_display = ("user", "background", "emotion", "emo_percent", "background", "created")