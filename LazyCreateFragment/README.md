# Lazy Creat Fragment in ViewPager / �� ViewPager ��������Fragment

## ����
�Ƚϳ���������������д setUserVisibleHint �������ɴ��ж� Fragment �Ƿ�ɼ������� onCreateView ���������� View �Ѵ����ı�־λ���ɼ����Ѵ��� View ʱȥ���� lazyload ������
�������һ���µķ����������������� Fragment �Ĵ������� Fragment ��Ϊ�ɼ�֮�󣬶��������ڵ� Fragment �ɼ���ʹ�����

## ԭ��
��д PagerAdapter.setPrimaryItem() ������ViewPager ͨ���˷���֪ͨ Adpater ��ǰ��ʾ�� Fragment����ʱ�Ŵ��������� Fragment��

## ����
�� ViewPager ���ͨ�� PagerAdapter.getItem() ȡ Fragment ʱ���������� Fragment��ֻ��һ����ʾ���ڼ��صĿ� Fragment���ڵ��� PagerAdapter.setPrimaryItem() ֪ͨ��ǰ������ʾ�� Fragment ʱ�Ŵ��������� Fragment��ͨ�� notifyDataSetChanged ֪ͨ ViewPager ������ȡ Fragment��

����ֱ�ӿ�����ɡ�

## ���� detach/destroy Ҳ������
�������Ҫ�л� Page ʱ�����һ�����ϵ� Page ���� detach/destroy�����Ե��� ViewPager.setOffscreenPageLimit(int limit)��Ĭ���ǻ������Ҹ�һҳ����໺�漸ҳlimit��ȡ���������Ͳ������л�����ʱ����ȥ�� onCreateView �ˡ�
 