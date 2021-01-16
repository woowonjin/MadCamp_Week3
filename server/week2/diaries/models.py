from django.db import models
from core import models as core_models

# Create your models here.
class Diary(core_models.TimeStampModel):
    text = models.TextField()
    user = models.ForeignKey("users.User", on_delete=models.CASCADE, related_name="diaries")
    background = models.ImageField(null=True, blank=True)
    date = models.CharField(max_length=20, default="0000-00-00")

    DEPRESSION = "우울"
    ANGER = "분노"
    ANXIETY = "불안"
    PAIN = "고통"
    PANIC = "당황"
    HAPPY = "행복"
    emo_choice = ((DEPRESSION, DEPRESSION), (ANGER, ANGER), (ANXIETY, ANXIETY), (PAIN, PAIN), (PANIC, PANIC), (HAPPY, HAPPY))
    emotion = models.CharField(choices=emo_choice, max_length=20, default=HAPPY)
    emo_percent = models.IntegerField()

    def __str__(self):
        return f"{self.text}"

    def serialize_custom(self):
        data = {
            "feed_date" : self.date,
            "feed_context" : self.text,
            "feed_likes" : self.likes.count(),
            "feed_pk" : self.pk,
            "emotion" : self.emotion,
            "percent" : self.emo_percent
        }
        return data