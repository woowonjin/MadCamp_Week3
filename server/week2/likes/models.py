from django.db import models
from core import models as core_models

# Create your models here.
class Like(core_models.TimeStampModel):
    user = models.ForeignKey("users.user", on_delete=models.CASCADE)
    diary = models.ForeignKey("diaries.Diary", on_delete=models.CASCADE, related_name="likes")

    def __str__(self):
        return f"{self.user.username} likes {self.diary.text}"